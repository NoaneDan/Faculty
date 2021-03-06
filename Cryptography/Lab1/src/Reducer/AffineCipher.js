
const affineCipher = (state, action) => {

  if (!state) {
    state = { mode: 'encrypt', cipherKey: '', plainText: '' };
  }

  switch (action.type) {
    case 'SET_KEY':
      return Object.assign({}, state, { cipherKey: action.value });
    case 'SET_PLAIN_TEXT':
      return Object.assign({}, state, { plainText: action.value });
    case 'SET_TRANSFORMED_TEXT':
      return Object.assign({}, state, { transformedText: action.value });
    case 'SET_MODE':
      return Object.assign({}, state, { mode: action.value });
    case 'SET_ERROR':
      return Object.assign({}, state, { error: action.value });
    default:
      return state;
  }
}

export { affineCipher };
