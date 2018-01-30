import { buildEvents, exec, translateOptions } from './utils'

/**
 * Popup Offer config object.
 * @typedef {BaseConfig} PopupOfferConfig
 */

const events = buildEvents('popupoffer', [
  'LOAD',
  'LOAD_FAIL',
  'OPEN',
  'CLOSE',
  'EXIT_APP',
  'START',
  'REWARD',
])

/**
 * See usage in {@link popupoffer}.
 * @protected
 */
class PopupOffer {
  static events = events

  /**
   * @protected
   * @param {PopupOfferConfig} opts - Initial config.
   */
  constructor(opts) {
    this.config({
      ...opts,
    })
  }

  /**
   * Update config.
   *
   * @param {PopupOfferConfig} opts - New config.
   * @returns {PopupOfferConfig} Updated config.
   */
  config(opts) {
    this._config = {
      ...this._config,
      ...opts,
    }
    return this._config
  }

  /**
   * @returns {Promise} Excutaion result promise.
   */
  prepare() {
    const options = {
      popupOfferId: this._config.id,
      ...this._config,
    }
    delete options.id
    return exec('preparePopupOffer', [translateOptions(options)])
  }

  /**
   * @returns {Promise} Excutaion result promise.
   */
  show() {
    return exec('showPopupOffer', [true])
  }

  /**
   * @returns {Promise} Excutaion result promise.
   */
  isReady() {
    return exec('isPopupOfferReady', [])
  }
}

export { PopupOffer }
