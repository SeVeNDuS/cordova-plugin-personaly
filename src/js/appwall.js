import { buildEvents, exec, translateOptions } from './utils'

/**
 * Reward Video config object.
 * @typedef {BaseConfig} AppWallConfig
 */

const events = buildEvents('appwall', [
  'LOAD',
  'LOAD_FAIL',
  'OPEN',
  'CLOSE',
  'EXIT_APP',
  'START',
  'REWARD',
])

/**
 * See usage in {@link appwall}.
 * @protected
 */
class AppWall {
  static events = events

  /**
   * @protected
   * @param {AppWallConfig} opts - Initial config.
   */
  constructor(opts) {
    this.config({
      ...opts,
    })
  }

  /**
   * Update config.
   *
   * @param {AppWallConfig} opts - New config.
   * @returns {AppWallConfig} Updated config.
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
      appWallId: this._config.id,
      ...this._config,
    }
    delete options.id
    return exec('showAppWall', [translateOptions(options)])
  }
}

export { AppWall }
