function formatDate(value) {
  if (!value) {
    return 'Not saved yet'
  }

  return new Intl.DateTimeFormat('en', {
    dateStyle: 'medium',
    timeStyle: 'short',
  }).format(new Date(value))
}

function GeneratedPostCard({ post, onCopy, copyStatus }) {
  if (!post) {
    return (
      <div className="empty-output">
        <span className="empty-mark">LC</span>
        <h3>Your LinkedIn draft will appear here</h3>
        <p>Complete the form and click Generate Post.</p>
      </div>
    )
  }

  return (
    <article className="generated-card">
      <div className="generated-meta">
        <span>{post.topic}</span>
        <span>{post.tone}</span>
        <span>{post.postType}</span>
        <span>{post.targetAudience}</span>
        <span>{formatDate(post.createdAt)}</span>
      </div>

      <div className="generated-content">
        {post.generatedContent}
      </div>

      <div className="generated-footer">
        <p>Draft generated for review. Please edit before posting.</p>
        <button className="secondary-button" type="button" onClick={onCopy}>
          {copyStatus || 'Copy Generated Post'}
        </button>
      </div>
    </article>
  )
}

export default GeneratedPostCard
