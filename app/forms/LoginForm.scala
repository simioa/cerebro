package forms

import play.api.data.Form
import play.api.data.Forms._

final case class LoginFormData(user: String, password: String)

object LoginFormData {
  def unapply(loginFormData: LoginFormData): Option[(String, String)] = {
    Some((loginFormData.user, loginFormData.password))
  }
}

object LoginForm {

  final val form = Form(
    mapping(
      "user" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginFormData.apply)(LoginFormData.unapply)
  )

}
