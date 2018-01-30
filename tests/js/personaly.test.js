const mockFn = jest.fn()
jest.mock('cordova/exec', () => mockFn)

const success = () => {}
const error = () => {}
const options = { isTesting: true }

const personaly = require('../../src/js/personaly')

test('setOptions() call correct native method', () => {
  personaly.setOptions(options, success, error)
  expect(mockFn).toBeCalledWith(success, error, 'Personaly', 'setOptions', [
    options,
  ])
})

describe('Interstitial', () => {
  it('prepare() call correct native method', () => {
    personaly.interstitial.prepare(options, success, error)
    expect(mockFn).toBeCalledWith(
      expect.any(Function),
      expect.any(Function),
      'Personaly',
      'prepareInterstitial',
      [options],
    )
  })

  it('show() call correct native method', () => {
    personaly.interstitial.show(success, error)
    expect(mockFn).toBeCalledWith(
      expect.any(Function),
      expect.any(Function),
      'Personaly',
      'showInterstitialAd',
      [true],
    )
  })
})

describe('Rewarded Video', () => {
  it('prepare() call correct native method', () => {
    personaly.rewardedvideo.prepare(options, success, error)
    expect(mockFn).toBeCalledWith(
      expect.any(Function),
      expect.any(Function),
      'Personaly',
      'prepareRewardedVideo',
      [options],
    )
  })

  it('show() call correct native method', () => {
    personaly.rewardedvideo.show(success, error)
    expect(mockFn).toBeCalledWith(
      expect.any(Function),
      expect.any(Function),
      'Personaly',
      'showRewardedVideo',
      [true],
    )
  })
})

describe('PopupOffer', () => {
  it('prepare() call correct native method', () => {
    personaly.popupoffer.prepare(options, success, error)
    expect(mockFn).toBeCalledWith(
      expect.any(Function),
      expect.any(Function),
      'Personaly',
      'preparePopupOffer',
      [options],
    )
  })

  it('show() call correct native method', () => {
    personaly.popupoffer.show(success, error)
    expect(mockFn).toBeCalledWith(
      expect.any(Function),
      expect.any(Function),
      'Personaly',
      'showPopupOffer',
      [true],
    )
  })
})
/*
describe('AppWall', () => {
  personaly.appwall.show(options, success, error)
  expect(mockFn).toBeCalledWith(
    expect.any(Function),
    expect.any(Function),
    'Personaly',
    'showAppWall',
    [options],
  )
})

describe('OfferWall', () => {
  personaly.offerwall.show(options, success, error)
  expect(mockFn).toBeCalledWith(
    expect.any(Function),
    expect.any(Function),
    'Personaly',
    'showOfferWall',
    [options],
  )
})
*/
