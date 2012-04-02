package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import anorm._

object Contact extends Controller {

	val Home = Redirect(routes.Contact.list(""))

	val contactForm: Form[models.Contact] = Form(
		mapping(
			"id" 			-> ignored(NotAssigned:Pk[Long]),
			"name"		-> nonEmptyText,
			"address"	-> nonEmptyText
		)(models.Contact.apply)(models.Contact.unapply)
	)

	def index = Action {
		Home
	}

	def list(order: String) = Action {
		val contacts = models.Contact.all(order)
		Ok(views.html.contact.list(contacts))
	}

	def create() = Action {
		Ok(views.html.contact.create(contactForm))
	}
	def insert() = Action { implicit request =>
		contactForm.bindFromRequest.fold(
			formWithError => BadRequest(views.html.contact.create(formWithError)),
			contact => {
				models.Contact.insert(contact)
				Home.flashing("success" -> "Contact %s has been created".format(contact.name))
			}
		)
	}

	def edit(id: Long) = Action {
		models.Contact.byId(id).map { contact =>
			Ok(views.html.contact.edit(id, contactForm.fill(contact)))
		}.getOrElse(NotFound)
	}

	def update(id: Long) = Action { implicit request =>
		contactForm.bindFromRequest.fold(
			formWithErrors => BadRequest(views.html.contact.edit(id, formWithErrors)),
			contact => {
				models.Contact.update(id, contact)
				Home.flashing("success" -> "Contact %s has been updated".format(contact.name))
			}
		)
	}

	def delete(id: Long) = Action {
		models.Contact.delete(id)
		Home.flashing("success" -> "Contact successfuly deleted")
	}

}
