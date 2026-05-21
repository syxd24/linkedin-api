import { useState } from 'react'

// AI-assisted draft reviewed and modified by the student.
function LoginPage({ onLogin }) {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  const handleSubmit = (event) => {
    event.preventDefault()
    setError('')

    const trimmedEmail = email.trim()

    if (!trimmedEmail || !password.trim()) {
      setError('Please enter both email address and password to continue.')
      return
    }

    const demoUser = { email: trimmedEmail }
    localStorage.setItem('demoUser', JSON.stringify(demoUser))
    onLogin(demoUser)
  }

  return (
    <main className="login-page">
      <section className="login-card" aria-labelledby="login-title">
        <div className="login-brand">
          <span className="brand-mark">LC</span>
          <span>LinkedIn Content AI</span>
        </div>

        <div className="login-heading">
          <h1 id="login-title">Welcome to LinkedIn Content AI</h1>
          <p>Sign in to access the LinkedIn post generator.</p>
        </div>

        <form className="login-form" onSubmit={handleSubmit}>
          <label>
            Email address
            <input
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              placeholder="example@email.com"
              autoComplete="email"
            />
          </label>

          <label>
            Password
            <input
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              placeholder="Enter any password"
              autoComplete="current-password"
            />
          </label>

          {error ? <p className="error-message">{error}</p> : null}

          <button className="primary-button" type="submit">
            Continue
          </button>
        </form>

        <p className="login-note">
          Demo login for thesis MVP. No real authentication is performed.
        </p>
      </section>
    </main>
  )
}

export default LoginPage
