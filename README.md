# Pepero Survival

###### (ko)

빼빼로 야생 (빼빼로 데이 기념 각별 Twitch 11/22/2020 방송) 구현

## 플러그인 설명

Spigot / Paper 1.16.4 (v1_16_R3) 용 빼빼로 야생 플러그인

x, z 좌표가 모두 특정 수(기본값 4)의 배수인 지점을 제외한 모든 블록이 제거되며,
블록 설치 또한 해당 좌표를 제외한 곳에서는 되지 않습니다.
또한, 물과 용암이 확산되지 않습니다. (조약돌 생성 불가, 흑요석 생성 가능)

## 구현 설명

더 좋은 방법을 찾지 못하여, 섹션 (청크) 팔레트를 변경하는 방식으로 구현하였습니다.
띠라서, 새로운 청크를 로드할 때 마다 MSPT (틱당 밀리초) 가 소폭 상승할 수 있습니다.

이미 로드된 청크에 대해서는 어떠한 변화도 주지 않으며,
플러그인보다 먼저 로드되는 스폰 지점 청크 등은 이 플러그인의 영향을 받지 않는 것으로 보여집니다.

## 간격 수정

기본값으로 x, z 좌표가 모두 4의 배수인 지점을 제외한 모든 블록이 제거됩니다.
간격을 변경하려면 1회 이상 실행한 서버에서 `plugins/PeperoSurvival` 폴더의 `config.yml`을 열고 `distance`를 수정해주시면 됩니다.

## 사용 환경

이 플러그인은 [noonmaru](https://github.com/noonmaru) 님의 [Kotlin Plugin](https://github.com/noonmaru/kotlin-plugin) 을 의존성으로 갖습니다.

이 플러그인을 사용하실 때 새로운 월드를 생성하고 (새로운 서버를 열거나 기존의 world 파일을 백업/지운 후) 사용하시기를 강력이 권고드립니다.
플러그인으로 인한 어떠한 문제 발생에 대해 책임지지 않습니다.

## 기타

사용에 어려움이 있거나, 궁금한 점이 있으면 [Issues](https://github.com/patrick-mc/pepero-survival/issues),
기여는 [Pull Request](https://github.com/patrick-mc/pepero-survival/pulls) 를 이용해주시면 감사하겠습니다.