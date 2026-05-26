# fishmod

Minecraft Forge 1.20.1 mod (`fishmod`) that adds fish items obtainable by fishing only.

## Run

```bash
./gradlew runClient
```

## Quick test

1. Start a world and fish in water.
2. Caught `fishmod` fish get per-item length (`cm`) and grade (`D/C/B/A/S`) data components.
3. Hover the fish item to see tooltip lines:
   - `Grade: <grade>` (colored, italic)
   - `Length: <value> cm` (gray, italic)

## Commands

Use `/give` for direct checks:

```mcfunction
/give @p fishmod:anchovy
/give @p fishmod:red_snapper
/give @p fishmod:tuna
```
