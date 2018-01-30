import exec from 'cordova/exec'

import { translateOptions } from './utils'

import { Interstitial } from './interstitial'
import { RewardedVideo } from './rewarded-video'
import { PopupOffer } from './popup-offer'
import { AppWall } from './appwall'
import { OfferWall } from './offerwall'

export const interstitial = new Interstitial()
export const rewardedvideo = new RewardedVideo()
export const popupoffer = new PopupOffer()
export const appwall = new AppWall()
export const offerwall = new OfferWall()

export function setOptions(options, successCallback, failureCallback) {
  if (typeof options === 'object') {
    Object.keys(options).forEach(k => {
      switch (k) {
        case 'interstitialAdId':
          interstitial._config.id = options[k]
          break
        case 'rewardedVideoId':
          rewardedvideo._config.id = options[k]
          break
        case 'offerWallId':
          offerwall._config.id = options[k]
          break
        case 'appWallId':
          appwall._config.id = options[k]
          break
        case 'popupOfferId':
          popupoffer._config.id = options[k]
          break
        case 'isTesting':
        case 'autoShow':
          interstitial._config[k] = options[k]
          rewardedvideo._config[k] = options[k]
          popupoffer._config[k] = options[k]
          appwall._config[k] = options[k]
          offerwall._config[k] = options[k]
          break
        default:
      }
    })
    exec(successCallback, failureCallback, 'Personaly', 'setOptions', [
      translateOptions(options),
    ])
  } else if (typeof failureCallback === 'function') {
    failureCallback('options should be specified.')
  }
}
