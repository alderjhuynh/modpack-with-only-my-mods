# Puppeteer

A data-driven player animation API for Fabric 26.2: keyframed offsets on  the vanilla humanoid body parts, turned on and off by a composable trigger-condition system, plus a one-shot `playOnce` call for discrete moments. Meant to be consumed by other mods (like my own modern Iron's reimagining, called Folio) and eventually authored through a separate visual/keyframe editor web app.

## iffy bits

- HumanoidModelAnimationMixin uses whole server ticks, not per-frame interp. It's fine at a stable 20 tps, but will look slightly stepped at high render frame rates.
- PlayerAnimationTracker re-evaluates every trigger for every online player every tick. 
- The tracker picks a single highest-priority animation. Splitting channels into independently resolved tracks would prolly fix it.