// LoginForm.kt
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

external interface LoginFormProps : RProps {
    var onSubmit: (String, String) -> Unit
}

val LoginForm = functionalComponent<LoginFormProps> { props ->
    var username by useState("")
    var password by useState("")

    form {
        attrs {
            onSubmitFunction = { event ->
                event.preventDefault()
                props.onSubmit(username, password)
            }
        }
        div {
            label {
                attrs {
                    htmlFor = "username"
                }
                +"Username:"
            }
            input(type = InputType.text) {
                attrs {
                    id = "username"
                    value = username
                    onChangeFunction = { event ->
                        val target = event.target as HTMLInputElement
                        username = target.value
                    }
                }
            }
        }
        div {
            label {
                attrs {
                    htmlFor = "password"
                }
                +"Password:"
            }
            input(type = InputType.password) {
                attrs {
                    id = "password"
                    value = password
                    onChangeFunction = { event ->
                        val target = event.target as HTMLInputElement
                        password = target.value
                    }
                }
            }
        }
        button(type = ButtonType.submit) {
            +"Login"
        }
    }
}
