# WIP

This project is still heavily in development. As my first project of this kind, it may take some time to get it where I want it.

# The Vail

**The Vail** is a Minecraft NeoForge mod centered around death, reincarnation, and progression through successive world cycles.

Death is not the end.

When a player dies, they cross the veil and advance into their next cycle. Each player progresses independently, meaning players who once traveled together may become separated by death — and may eventually meet again if their cycles align.

## Minecraft / NeoForge Version

- **Minecraft:** 1.21.1
- **NeoForge:** 21.1.229
- **Java:** Java 21

## Current Features

### Player Cycles

Every player has their own cycle number.

When a player dies:

1. Their cycle increases.
2. They cross into the next cycle.
3. Their new cycle determines where they are sent.
4. Other players remain in their current cycles until they die.

This allows multiplayer groups to naturally become separated and reunited throughout a playthrough.

### Shared Cycle Worlds

Players on the same cycle share the same cycle location.

For example:

- Player A dies and enters Cycle 1.
- Player B is still alive in Cycle 0.
- Player B later dies and enters Cycle 1.
- Both players can now exist together in the same cycle again.

Each cycle has its own stored spawn location.

### Persistent Player Data

Player cycle progression persists between server restarts and world reloads.

Player cycle data is stored in:

```text
world/thevail/player_cycles.json
