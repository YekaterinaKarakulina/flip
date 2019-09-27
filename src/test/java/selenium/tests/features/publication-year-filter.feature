Feature: Publication year filter
  #Tests checks working of "Publication year" filter in "flip.kz" website

  Background:
    Given Website flip.kz is opened
    When User does login

  Scenario:
    Given User is authorized
    Then User navigates to imaginative literature section
    And User set publication year first value
    And User moves to random result`s page, selects random book
    Then Random book`s publication year from result page is greater or equal then user entered

