Feature: GitHub Repository Labels
  As a GitHub user
  I want to manage labels in a repository
  So that I can categorize and organize issues effectively

  Background:
    Given I have a valid GitHub repository ID

  # Happy Path
  Scenario: Create a new label successfully
    Given I have a unique label name "Bug11" with color "FF0000" and description "Label for bug issues"
    When I send a createLabel mutation to the GitHub GraphQL API
    Then the API should respond with status code 200
    And label should have correct details




