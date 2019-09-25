Feature: One author filter
  #Test checks working of "Authors" filter in "flip.kz" website with one author"

  Background:
    Given User open browser on website flip.kz
    When User enter email in input field
    And User enter password in input field
    Then User became current user

  Scenario:
    Given User navigate to imaginative literature section
    When User click random author
    Then Random book from result page has author which user click


