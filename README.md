# fishmod

Minecraft Forge 1.20.1 mod (`fishmod`) that ports Aquaculture fish items and makes fishing fish-only.

## Run

```bash
./gradlew runClient
```

## Quick test

1. Start a world and fish in water.
2. Fishing only returns `fishmod` fish items (no vanilla fish/treasure/junk).
3. Hover a fish item to see:
   - `Species Grade: <E/D/C/B/A>` (decorative)
   - `Size: <White/Yellow/Purple>`
   - `Size: <multiplier>x`
4. Eating fish gives size-tier hunger bonus:
   - White: +0
   - Yellow: +1
   - Purple: +2
