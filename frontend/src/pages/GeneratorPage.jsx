import { useState } from 'react'
import Header from '../components/Header.jsx'
import PostForm from '../components/PostForm.jsx'
import GeneratedPostCard from '../components/GeneratedPostCard.jsx'
import { generatePost } from '../api/postApi.js'

const initialFormData = {
  topic: '',
  tone: 'Professional',
  postType: 'Educational',
  targetAudience: '',
  optionalContext: '',
}

function GeneratorPage({ currentPage, user, onLogout, onNavigate }) {
  const [formData, setFormData] = useState(initialFormData)
  const [generatedPost, setGeneratedPost] = useState(null)
  const [formError, setFormError] = useState('')
  const [isGenerating, setIsGenerating] = useState(false)
  const [copyStatus, setCopyStatus] = useState('')

  const handleChange = (event) => {
    const { name, value } = event.target
    setFormData((current) => ({
      ...current,
      [name]: value,
    }))
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setFormError('')
    setCopyStatus('')

    if (!formData.topic.trim() || !formData.targetAudience.trim()) {
      setFormError('Please enter a topic and target audience before generating.')
      return
    }

    setIsGenerating(true)

    try {
      const post = await generatePost({
        topic: formData.topic.trim(),
        tone: formData.tone,
        postType: formData.postType,
        targetAudience: formData.targetAudience.trim(),
        optionalContext: formData.optionalContext.trim(),
      })
      setGeneratedPost(post)
    } catch (error) {
      setFormError(error.message)
    } finally {
      setIsGenerating(false)
    }
  }

  const handleClear = () => {
    setFormData(initialFormData)
    setGeneratedPost(null)
    setFormError('')
    setCopyStatus('')
  }

  const handleCopy = async () => {
    if (!generatedPost?.generatedContent) {
      return
    }

    try {
      await navigator.clipboard.writeText(generatedPost.generatedContent)
      setCopyStatus('Copied')
      window.setTimeout(() => setCopyStatus(''), 1800)
    } catch {
      setCopyStatus('Copy failed')
    }
  }

  return (
    <div className="site-shell generator-shell">
      <Header
        currentPage={currentPage}
        user={user}
        onLogout={onLogout}
        onNavigate={onNavigate}
      />

      <main className="generator-main">
        <aside className="form-panel">
          <div className="panel-intro">
            <h1>LinkedIn Post Generator</h1>
            <p>Fill in the form below to generate a LinkedIn post draft.</p>
          </div>

          <PostForm
            formData={formData}
            error={formError}
            isLoading={isGenerating}
            onChange={handleChange}
            onSubmit={handleSubmit}
            onClear={handleClear}
          />
        </aside>

        <section className="output-panel">
          <div className="output-toolbar">
            <div>
              <h2>Post Output</h2>
              <span className="status-pill">
                {generatedPost ? 'Draft generated' : 'Waiting for generation'}
              </span>
            </div>
            <button
              type="button"
              className="secondary-button"
              disabled={!generatedPost}
              onClick={handleCopy}
            >
              {copyStatus || 'Copy'}
            </button>
          </div>

          <GeneratedPostCard
            post={generatedPost}
            onCopy={handleCopy}
            copyStatus={copyStatus}
          />
        </section>
      </main>
    </div>
  )
}

export default GeneratorPage
