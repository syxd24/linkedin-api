const tones = [
  'Professional',
  'Friendly',
  'Inspirational',
  'Educational',
  'Confident',
]

const postTypes = [
  'Educational',
  'Storytelling',
  'Promotional',
  'Thought Leadership',
  'Career Advice',
]

function PostForm({
  formData,
  error,
  isLoading,
  onChange,
  onSubmit,
  onClear,
}) {
  return (
    <form className="generator-form" onSubmit={onSubmit}>
      <label>
        Topic *
        <textarea
          name="topic"
          value={formData.topic}
          onChange={onChange}
          placeholder="Example: How junior developers can improve problem-solving skills"
          rows="3"
          required
        />
      </label>

      <label>
        Tone *
        <select name="tone" value={formData.tone} onChange={onChange}>
          {tones.map((tone) => (
            <option value={tone} key={tone}>
              {tone}
            </option>
          ))}
        </select>
      </label>

      <label>
        Post type *
        <select name="postType" value={formData.postType} onChange={onChange}>
          {postTypes.map((postType) => (
            <option value={postType} key={postType}>
              {postType}
            </option>
          ))}
        </select>
      </label>

      <label>
        Target audience *
        <input
          type="text"
          name="targetAudience"
          value={formData.targetAudience}
          onChange={onChange}
          placeholder="Example: Junior software developers"
          required
        />
      </label>

      <label>
        Optional context
        <textarea
          name="optionalContext"
          value={formData.optionalContext}
          onChange={onChange}
          placeholder="Mention any specific points, examples, or context..."
          rows="5"
        />
      </label>

      {error ? <p className="error-message">{error}</p> : null}

      <div className="form-actions">
        <button className="primary-button" type="submit" disabled={isLoading}>
          {isLoading ? 'Generating...' : 'Generate Post'}
        </button>
        <button className="secondary-button" type="button" onClick={onClear}>
          Clear Form
        </button>
      </div>
    </form>
  )
}

export default PostForm
