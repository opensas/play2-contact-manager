package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Contact(
	id: Pk[Long]			= NotAssigned,
	name: String 			= "unknown name",
	address: String 	= "unknown address"
)

object Contact {

	val contact = {
		get[Pk[Long]]("id") ~
		get[String]("name") ~
		get[String]("address") map {
			case id~name~address => Contact(id, name, address)
		}
	}

	def byId(id: Long): Option[Contact] = {
		DB.withConnection { implicit c =>
			SQL("""
				select * from contacts where id = {id}
			""").on(
				'id -> id
			).as(contact.singleOpt)
		}
	}

	def all(order: String = "name"): Seq[Contact] = DB.withConnection { implicit c =>
		SQL(
			"select * from contacts" +
			( if (order!="") { " order by " + order } else { "" } )
		).as(contact *)
	}

	def insert(contact: Contact) = DB.withConnection { implicit c =>
		SQL("""
			insert into contacts(
				name, address
			) values (
				{name}, {address}
			)
		""").on(
			'name			-> contact.name,
			'address	-> contact.address
		).executeUpdate()
	}

	def update(contact: Contact): Unit = update(contact.id.get, contact)

	def update(id: Long, contact: Contact): Unit = DB.withConnection { implicit c =>
		SQL("""
			update contacts set
				name = {name},
				address = {address}
			where
				id = {id}
		""").on(
			'id				-> id,
			'name			-> contact.name,
			'address	-> contact.address
		).executeUpdate()
	}

	def delete(id: Long) = DB.withConnection { implicit c =>
		SQL(
			"delete from contacts where id = {id}"
		).on('id -> id).executeUpdate()
	}
}
