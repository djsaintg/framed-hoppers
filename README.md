# 🧲 Framed Hoppers

**Unofficial Fabric mod** for Minecraft 26.3: put an item frame on a hopper, and that hopper only takes in the item shown.

Frame an iron ingot on a hopper under your mob farm's chest line, and only iron goes in. Every other item passes along to the next hopper. You get an item sorter with no comparators, no redstone and no stacks of renamed filler items.

`Java 25` `Fabric Loader 0.20.1` `Mixin` `Minecraft 26.3`

**v1.0.0 — Stable**

---

## ✨ Features
- 🖼️ **Frame = filter**: a hopper with a filled item frame on any side only accepts that item
- 🧺 **Several frames, several items**: put frames on more than one side to let in several different items
- 🔁 **Every input**: works for hoppers and chests above, hoppers pointing in from the side, and items dropped on top
- 🪹 **Empty frame, normal hopper**: no frame (or an empty one) means a regular vanilla hopper
- ✨ **Glow item frames count too**
- 🪶 **Server side only**: no textures, no new blocks, no Fabric API. Vanilla hoppers just behave better

## 🎯 How matching works
Only the item type counts, not its name, damage or enchantments. A framed enchanted book lets in every enchanted book, and a framed iron sword lets in any iron sword.

## 📦 Install
1. Install Fabric Loader 0.20.1+ for Minecraft 26.3
2. Drop `framed-hoppers-1.0.0.jar` into `mods/`

Works in singleplayer and on Fabric servers. Players joining a server don't need it.

## 🛠️ Build
```bash
JAVA_HOME=/path/to/jdk-25 ./gradlew build
# -> build/libs/framed-hoppers-1.0.0.jar
```

## 🗂️ How it works
- `HopperBlockEntityMixin`: every item that goes into a hopper passes through vanilla's `canPlaceItemInContainer`. The mixin refuses items the hopper's frames don't show
- `FramedHoppers`: finds the item frames hanging on a hopper. Each hopper looks them up at most once per tick

## 📄 License
MIT

*Code with rhythm. Ship with grace.*
