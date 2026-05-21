import Header from '../components/Header.jsx'

const features = [
  {
    title: 'Topic-Based Generation',
    text: 'Create a focused draft from a clear topic and supporting context.',
  },
  {
    title: 'Tone and Post Type Control',
    text: 'Select the tone and content format that match your communication goal.',
  },
  {
    title: 'Content History',
    text: 'Review previously generated drafts and load them back into the preview.',
  },
  {
    title: 'Copy-Ready Drafts',
    text: 'Copy generated text quickly after reviewing and editing the final wording.',
  },
]

function HomePage({ currentPage, user, onLogout, onNavigate }) {
  return (
    <div className="site-shell">
      <Header
        currentPage={currentPage}
        user={user}
        onLogout={onLogout}
        onNavigate={onNavigate}
      />

      <main>
        <section className="hero-section">
          <div className="hero-content">
            <span className="eyebrow">AI-powered - LinkedIn drafts</span>
            <h1>
              AI-Powered LinkedIn
              <span> Content Generation</span>
            </h1>
            <p>
              Generate professional LinkedIn post drafts by entering a topic,
              tone, post type, target audience, and optional context.
            </p>
            <div className="hero-actions">
              <button
                type="button"
                className="primary-button"
                onClick={() => onNavigate('generator')}
              >
                Start Generating
              </button>
              <button
                type="button"
                className="secondary-button"
                onClick={() => onNavigate('generator')}
              >
                View Demo Flow
              </button>
            </div>
          </div>
        </section>

        <section className="stats-strip" aria-label="Application highlights">
          <div>
            <strong>5</strong>
            <span>input fields</span>
          </div>
          <div>
            <strong>5</strong>
            <span>tone options</span>
          </div>
          <div>
            <strong>100%</strong>
            <span>draft output</span>
          </div>
        </section>

        <section className="feature-section" id="features">
          {features.map((feature, index) => (
            <article className="feature-card" key={feature.title}>
              <span>{index + 1}</span>
              <h2>{feature.title}</h2>
              <p>{feature.text}</p>
            </article>
          ))}
        </section>

        <section className="scope-section">
          <span className="eyebrow">MVP scope</span>
          <h2>LinkedIn draft generation only</h2>
          <p>
            This application generates LinkedIn post drafts only. It does not
            publish, schedule, or analyze LinkedIn posts.
          </p>
          <button
            type="button"
            className="secondary-button"
            onClick={() => onNavigate('generator')}
          >
            Open Generator
          </button>
        </section>
      </main>
    </div>
  )
}

export default HomePage
