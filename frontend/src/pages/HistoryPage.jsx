import { useEffect, useState } from 'react'
import Header from '../components/Header.jsx'
import { getPostHistory, deletePost, getPostById } from '../api/postApi.js'

function formatDate(value) {
  if (!value) {
    return 'Unknown date'
  }

  return new Intl.DateTimeFormat('en', {
    dateStyle: 'medium',
    timeStyle: 'short',
  }).format(new Date(value))
}

function HistoryPage({ currentPage, user, onLogout, onNavigate }) {
  const [posts, setPosts] = useState([])
  const [selectedPost, setSelectedPost] = useState(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState('')
  const [deleteError, setDeleteError] = useState('')

  useEffect(() => {
    let isActive = true

    getPostHistory()
      .then((data) => {
        if (!isActive) return

        setPosts(Array.isArray(data) ? data : [])
        if (Array.isArray(data) && data.length === 0) {
          setSelectedPost(null)
        }
      })
      .catch((error) => {
        if (!isActive) return

        if (error.message.includes('Backend server is not available')) {
          setError('Backend server is not available. Please start the Spring Boot backend.')
        } else {
          setError('Something went wrong while loading history. Please try again.')
        }
        setPosts([])
      })
      .finally(() => {
        if (isActive) {
          setIsLoading(false)
        }
      })

    return () => {
      isActive = false
    }
  }, [])

  const handleView = async (id) => {
    setDeleteError('')
    try {
      const post = await getPostById(id)
      setSelectedPost(post)
    } catch {
      setDeleteError('Something went wrong while loading the post. Please try again.')
    }
  }

  const handleDelete = async (id) => {
    setDeleteError('')
    const confirmed = window.confirm(
      'Are you sure you want to delete this post? This action cannot be undone.',
    )
    if (!confirmed) return

    try {
      await deletePost(id)
      setPosts((prev) => prev.filter((post) => post.id !== id))
      if (selectedPost?.id === id) {
        setSelectedPost(null)
      }
    } catch {
      setDeleteError('Something went wrong while deleting the post. Please try again.')
    }
  }

  return (
    <div className="site-shell">
      <Header
        currentPage={currentPage}
        user={user}
        onLogout={onLogout}
        onNavigate={onNavigate}
      />

      <main className="history-page">
        <div className="history-container">
          <div className="history-list-panel">
            <div className="history-panel-header">
              <h1>Generated Posts</h1>
              {isLoading ? <span className="status-pill">Loading</span> : null}
            </div>

            {error ? (
              <div className="error-message">{error}</div>
            ) : !isLoading && posts.length === 0 ? (
              <div className="history-empty-state">
                <p>No generated posts yet.</p>
                <button
                  type="button"
                  className="primary-button"
                  onClick={() => onNavigate('generator')}
                >
                  Generate Your First Post
                </button>
              </div>
            ) : (
              <div className="history-items">
                {posts.map((post) => (
                  <div
                    key={post.id}
                    className={`history-item-card ${selectedPost?.id === post.id ? 'active' : ''}`}
                    onClick={() => handleView(post.id)}
                    role="button"
                    tabIndex={0}
                    onKeyDown={(e) => {
                      if (e.key === 'Enter' || e.key === ' ') handleView(post.id)
                    }}
                  >
                    <div className="item-content">
                      <h3>{post.topic}</h3>
                      <div className="item-meta">
                        <span className="badge">{post.tone}</span>
                        <span className="badge">{post.postType}</span>
                      </div>
                      <p className="item-date">{formatDate(post.createdAt)}</p>
                    </div>
                    <button
                      type="button"
                      className="delete-btn"
                      onClick={(e) => {
                        e.stopPropagation()
                        handleDelete(post.id)
                      }}
                      aria-label="Delete post"
                    >
                      x
                    </button>
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="history-detail-panel">
            {deleteError ? (
              <div className="error-message">{deleteError}</div>
            ) : null}

            {selectedPost ? (
              <article className="history-detail-card">
                <div className="detail-meta">
                  <div>
                    <h2>{selectedPost.topic}</h2>
                    <div className="detail-badges">
                      <span className="badge">{selectedPost.tone}</span>
                      <span className="badge">{selectedPost.postType}</span>
                      <span className="badge">{selectedPost.targetAudience}</span>
                    </div>
                    <p className="detail-date">{formatDate(selectedPost.createdAt)}</p>
                  </div>
                </div>

                <div className="detail-content">
                  <h3>Generated Content</h3>
                  <p className="generated-text">{selectedPost.generatedContent}</p>
                </div>

                {selectedPost.optionalContext ? (
                  <div className="detail-context">
                    <h3>Additional Context</h3>
                    <p>{selectedPost.optionalContext}</p>
                  </div>
                ) : null}

                <div className="detail-actions">
                  <button
                    type="button"
                    className="primary-button"
                    onClick={() => {
                      navigator.clipboard.writeText(selectedPost.generatedContent)
                      alert('Post copied to clipboard!')
                    }}
                  >
                    Copy Content
                  </button>
                  <button
                    type="button"
                    className="danger-button"
                    onClick={() => handleDelete(selectedPost.id)}
                  >
                    Delete Post
                  </button>
                </div>
              </article>
            ) : (
              <div className="empty-output">
                <span className="empty-mark">LC</span>
                <h3>Select a post to view</h3>
                <p>Click on a post from the list to see its full content.</p>
              </div>
            )}
          </div>
        </div>
      </main>
    </div>
  )
}

export default HistoryPage
