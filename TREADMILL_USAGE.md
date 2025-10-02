# Guinea Treadmill Usage Guide

## Overview
The Guinea Treadmill is a block that generates redstone power when guinea pigs walk on it. It requires water and hay to function, which must be periodically refilled.

## Features

### Resource System
- **Water Level**: Ranges from 0 to 10
- **Hay Level**: Ranges from 0 to 10
- Both resources start at maximum (10) when the block is placed
- Each time a guinea pig steps on the treadmill, it consumes 1 water and 1 hay

### Redstone Power
- The treadmill emits redstone power (level 15) when:
  1. A guinea pig is standing on it
  2. Both water level > 0 AND hay level > 0
- If resources are depleted, the treadmill will not generate power even if a guinea pig is on it

### Refilling Resources

#### Water
- Right-click the treadmill with a **water bucket**
- The water level will be refilled to maximum (10)
- In survival mode, the water bucket becomes an empty bucket

#### Hay
- Right-click the treadmill with **timothy hay**
- The hay level will be refilled to maximum (10)
- In survival mode, one timothy hay item is consumed

## Usage Example

1. Place a Guinea Treadmill block
2. Connect redstone components (e.g., redstone dust, lamps, droppers) to the treadmill
3. Guinea pigs will naturally seek out and walk on the treadmill
4. Each time they step on it, redstone power is emitted for ~2.25 seconds (45 ticks)
5. Monitor resource levels and refill as needed:
   - Use water buckets when water runs low
   - Use timothy hay when hay runs low
6. If resources are depleted, refill both before the treadmill will work again

## Technical Details
- Water and hay consumption: 1 unit per activation
- Maximum capacity: 10 units each
- This means each treadmill can be used 10 times before needing a refill
- Power duration: 45 ticks (~2.25 seconds) per activation
