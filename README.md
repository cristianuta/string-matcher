# string-pattern-match
Generic String pattern Match with Regexp

Aplicatie Spring Boot (Maven) care gaseste **toate aparitiile** unei secvente ADN
(ex: `GATTACA`) intr-un sir mare format din literele `A`, `C`, `G`, `T`.

## Pornire

```bash
mvn spring-boot:run
```

Apoi deschide http://localhost:8080

## Utilizare

1. Lipeste sirul ADN in campul mare (spatiile si liniile noi sunt ignorate, literele mici sunt acceptate).
2. Scrie secventa cautata si apasa **Cauta** (sau Enter).
3. Se afiseaza numarul total de aparitii; navighezi cu **Next** / **Prev** (sau Enter / Shift+Enter).
   Aparitia curenta e selectata in text si afisata cu context mai jos.

Sunt numarate si aparitiile care se suprapun (ex: `AA` in `AAAA` = 3 aparitii),
folosind un lookahead regex `(?=SECVENTA)`.

## API

`POST /api/search`

```json
{ "sequence": "ACGTACGT", "pattern": "ACG" }
```

Raspuns:

```json
{ "sequence": "ACGTACGT", "pattern": "ACG", "count": 2, "positions": [0, 4] }
```

Pozitiile sunt 0-based, pe sirul normalizat (fara spatii, cu litere mari).
