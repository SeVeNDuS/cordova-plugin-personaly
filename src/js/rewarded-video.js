import { buildEvents, exec, translateOptions } from './utils'

/**
 * Reward Video config object.
 * @typedef {BaseConfig} RewardedVideoConfig
 */

const events = buildEvents('rewardedvideo', [
  'LOAD',
  'LOAD_FAIL',
  'OPEN',
  'CLOSE',
  'EXIT_APP',
  'START',
  'REWARD',
])

/**
 * See usage in {@link rewardedvideo}.
 * @protected
 */
class RewardedVideo {
  static events = events

  /**
   * @protected
   * @param {RewardedVideoConfig} opts - Initial config.
   */
  constructor(opts) {
    this.config({
      ...opts,
    })
  }

  /**
   * Update config.
   *
   * @param {RewardedVideoConfig} opts - New config.
   * @returns {RewardedVideoConfig} Updated config.
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
      rewardedVideoId: this._config.id,
      ...this._config,
    }
    delete options.id
    return exec('prepareRewardedVideo', [translateOptions(options)])
  }

  /**
   * @returns {Promise} Excutaion result promise.
   */
  show() {
    return exec('showRewardedVideo', [true])
  }

  /**
   * @returns {Promise} Excutaion result promise.
   */
  isReady() {
    return exec('isRewardedVideoReady', [])
  }
}

export { RewardedVideo }
