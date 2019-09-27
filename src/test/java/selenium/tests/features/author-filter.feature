Feature: One author filter
  #Test checks working of "Authors" filter in "flip.kz" website with one author

  Background:
    Given Website flip.kz is opened
    When User does login

  Scenario:
    Given User is authorized
    When User navigates to imaginative literature section
    And User selects random book author, moves to random result`s page, selects random book
    Then Random book`s author from result page is selected author

  Scenario:
    Given User is authorized
    Then User navigates to imaginative literature section
    And User selects random book authors, moves to random result`s page, selects random book
    Then Random book`s author from result page is one of the selected authors

