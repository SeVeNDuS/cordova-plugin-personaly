import { buildEvents, exec, translateOptions } from './utils'

/**
 * Reward Video config object.
 * @typedef {BaseConfig} OfferWallConfig
 */

const events = buildEvents('offerwall', [
  'LOAD',
  'LOAD_FAIL',
  'OPEN',
  'CLOSE',
  'EXIT_APP',
  'START',
  'REWARD',
])

/**
 * See usage in {@link offerwall}.
 * @protected
 */
class OfferWall {
  static events = events

  /**
   * @protected
   * @param {OfferWallConfig} opts - Initial config.
   */
  constructor(opts) {
    this.config({
      ...opts,
    })
  }

  /**
   * Update config.
   *
   * @param {OfferWallConfig} opts - New config.
   * @returns {OfferWallConfig} Updated config.
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
  show() {
    const options = {
      offerWallId: this._config.id,
      ...this._config,
    }
    delete options.id
    return exec('showOfferWall', [translateOptions(options)])
  }
}

export { OfferWall }
