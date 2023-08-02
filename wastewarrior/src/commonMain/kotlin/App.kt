// App.kt
import react.*
import react.dom.*
import kotlin.browser.*

external interface AppState : RState {
    var loggedIn: Boolean
}

class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
        loggedIn = false
    }

    private fun handleLogin(username: String, password: String) {
        // Perform login logic here
        // For simplicity, we'll just set loggedIn to true if username and password are not empty
        if (username.isNotBlank() && password.isNotBlank()) {
            setState {
                loggedIn = true
            }
        }
    }

    override fun RBuilder.render() {
        div {
            if (state.loggedIn) {
                h1 { +"Welcome! You are logged in." }
            } else {
                h1 { +"Login Page" }
                LoginForm {
                    onSubmit = { username, password -> handleLogin(username, password) }
                }
            }
        }
    }
}

fun main() {
    render(document.getElementById("root")) {
        child(App::class) {}
    }
}
