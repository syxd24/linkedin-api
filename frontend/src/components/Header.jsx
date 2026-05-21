function Header({ currentPage, user, onLogout, onNavigate }) {
  return (
    <header className="app-header">
      <button
        type="button"
        className="brand"
        onClick={() => onNavigate('home')}
        aria-label="Go to homepage"
      >
        <span className="brand-mark">LC</span>
        <span>LinkedIn Content AI</span>
      </button>

      <nav className="nav-links" aria-label="Main navigation">
        <button
          type="button"
          className={currentPage === 'home' ? 'active' : ''}
          onClick={() => onNavigate('home')}
        >
          Home
        </button>
        <button
          type="button"
          className={currentPage === 'generator' ? 'active' : ''}
          onClick={() => onNavigate('generator')}
        >
          Generator
        </button>
        <button
          type="button"
          className={currentPage === 'history' ? 'active' : ''}
          onClick={() => onNavigate('history')}
        >
          History
        </button>
      </nav>

      <button
        type="button"
        className="header-cta"
        onClick={() => onNavigate('generator')}
      >
        Start Generating
      </button>

      <div className="header-user">
        {user?.email ? <span>Signed in as {user.email}</span> : null}
        <button type="button" className="logout-button" onClick={onLogout}>
          Logout
        </button>
      </div>
    </header>
  )
}

export default Header
