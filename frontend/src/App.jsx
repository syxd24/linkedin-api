import { useState } from 'react'
import LoginPage from './pages/LoginPage.jsx'
import HomePage from './pages/HomePage.jsx'
import GeneratorPage from './pages/GeneratorPage.jsx'
import HistoryPage from './pages/HistoryPage.jsx'

// AI-assisted draft reviewed and modified by the student.
function App() {
  const [currentPage, setCurrentPage] = useState('home')
  const [demoUser, setDemoUser] = useState(() => {
    const storedUser = localStorage.getItem('demoUser')

    if (!storedUser) {
      return null
    }

    try {
      return JSON.parse(storedUser)
    } catch {
      localStorage.removeItem('demoUser')
      return null
    }
  })

  const navigateTo = (page) => {
    setCurrentPage(page)
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  const handleLogin = (user) => {
    setDemoUser(user)
    setCurrentPage('home')
  }

  const handleLogout = () => {
    localStorage.removeItem('demoUser')
    setDemoUser(null)
    setCurrentPage('home')
  }

  if (!demoUser) {
    return <LoginPage onLogin={handleLogin} />
  }

  if (currentPage === 'generator') {
    return (
      <GeneratorPage
        currentPage={currentPage}
        user={demoUser}
        onLogout={handleLogout}
        onNavigate={navigateTo}
      />
    )
  }

  if (currentPage === 'history') {
    return (
      <HistoryPage
        currentPage={currentPage}
        user={demoUser}
        onLogout={handleLogout}
        onNavigate={navigateTo}
      />
    )
  }

  return (
    <HomePage
      currentPage={currentPage}
      user={demoUser}
      onLogout={handleLogout}
      onNavigate={navigateTo}
    />
  )
}

export default App
