const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:15150'
const TECHNICAL_ERROR_PATTERN = /exception|sql|java|spring|stacktrace|trace/i

function isUserFriendlyMessage(message) {
  return (
    typeof message === 'string' &&
    message.trim() &&
    !TECHNICAL_ERROR_PATTERN.test(message) &&
    message !== 'Internal Server Error'
  )
}

async function request(path, options = {}) {
  try {
    const response = await fetch(`${API_BASE_URL}${path}`, {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    })

    if (!response.ok) {
      let message = 'Something went wrong. Please try again.'

      try {
        const errorBody = await response.json()
        if (isUserFriendlyMessage(errorBody.message)) {
          message = errorBody.message
        } else if (isUserFriendlyMessage(errorBody.error)) {
          message = errorBody.error
        }
      } catch {
        message = 'Something went wrong. Please try again.'
      }

      throw new Error(message)
    }

    if (response.status === 204) {
      return null
    }

    return response.json()
  } catch (error) {
    if (error.name === 'TypeError') {
      throw new Error(
        'Backend server is not available. Please start the Spring Boot backend.',
        { cause: error },
      )
    }

    throw error
  }
}

export function generatePost(payload) {
  return request('/api/posts/generate', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export function getPostHistory() {
  return request('/api/posts/history')
}

export function getPostById(id) {
  return request(`/api/posts/${id}`)
}

export function deletePost(id) {
  return request(`/api/posts/${id}`, {
    method: 'DELETE',
  })
}
