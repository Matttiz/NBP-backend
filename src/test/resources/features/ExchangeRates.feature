Feature: Kursy walut

  Scenario: Kursy walut
    Given Pobrano kursy walut
    When Wyświetl kurs dla waluty o kodzie: "USD"
    And Wyświetl kurs dla waluty o nazwie: "dolar amerykański"
    And Wyświetl waluty o kursie powyżej: 5
    Then Wyświetl waluty o kursie poniżej: 3
