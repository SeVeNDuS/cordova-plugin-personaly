import corodvaExec from 'cordova/exec'

/**
 * Base config object.
 * @typedef {Object} BaseConfig
 * @property {string} [id=TESTING_AD_ID] - Ad Unit ID
 * @property {boolean} [isTesting=false] - Receiving test ad
 * @property {boolean} [autoShow=false] - Auto show ad when loaded
 *
 * @property {string|null} [gender=null]
 * Default is not calling `setGender`.
 * Set to `male` or `famel`.
 *
 * @property {string|null} [dateOfBirth=null]
 * Default is not calling `setDateOfBirth`.
 * Set to `YYYY/MM/DD`
 *
 * @property {string|null} [age=null]
 * Default is not calling `setAge`.
 * Set to number containing the age of the user
 *
 * @property {string|null} [userId=null]
 * Default is not calling `setUserId`.
 * Set to your internal userid
 */

/**
 * @ignore
 */
export function exec(method, args) {
  return new Promise((resolve, reject) => {
    corodvaExec(resolve, reject, 'Personaly', method, args)
  })
}

/**
 * @ignore
 */
export function isFunction(x) {
  return typeof x === 'function'
}

/**
 * @ignore
 */
export function wrapCallbacks(p, successCallback, failureCallback) {
  if (isFunction(successCallback)) {
    p = p.then(successCallback) // eslint-disable-line no-param-reassign
  }
  if (isFunction(failureCallback)) {
    p = p.catch(failureCallback) // eslint-disable-line no-param-reassign
  }
  return p
}

/**
 * @ignore
 */
export function translateOptions(options) {
  const opts = {}

  return {
    ...options,
    ...opts,
  }
}

/**
 * @ignore
 */
export function buildEvents(adType, eventKeys) {
  return eventKeys.reduce((acc, eventKey) => {
    acc[eventKey] = `personaly.${adType}.events.${eventKey}`
    return acc
  }, {})
}
