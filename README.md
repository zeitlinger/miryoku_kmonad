# APTex keyboard

Goals

- Fast typing
  - No home row mods on base layer that easily interfere with typing
  - No auto shift or tap dance
  - combos for the most common bigrams and trigrams
  - combos for the most common same finger bigrams
- No lateral movement
  - Inner columns are not used
- All modifiers can be used with all keys
  - All modifiers except GUI can be used with all keys
- Support for German umlauts and the most common IntelliJ shortcuts

Features

- 34 keys, but only 26 keys are needed for typing
- [APTex](https://github.com/Apsu/APTex) layout with slight modifications
- Home row mods (Alt, Ctrl, Shift) on all layers, except the base layer where combos are used instead to avoid timing issues (see [Modifiers](#modifiers))
- In the base layer, you have to press space and the modifier key at the same time to activate the modifier.
  This is to avoid accidental activation of the modifier.
- In the shift layer, you can disable shift with the index finger that would usually enable shift.
- Combos for keys in the two center columns,
  so lateral movement is not needed, except for special combos like `Ctrl+B`
- Combos for the most common bigrams, reducing SFBs from 0.70% to 0.26%
- Combos for the most common trigrams
- "Next sentence" key (". ", capitalizes the next letter)
- Needs https://github.com/bdaase/remove-alt-tab-delay on Ubuntu

Credits

- [The T-34 keyboard layout](https://www.jonashietala.se/blog/2021/06/03/the-t-34-keyboard-layout/)
- [APTex](https://github.com/Apsu/APTex)

Notes

- The keyboard layout is generated using [generateKeyboard](generateKeyboard.kt) from this file.  => link to [Generator](#generator)
- The generated keyboard can be found in a
  [fork of mini-ryoku](https://github.com/zeitlinger/mini-ryoku/blob/main/qmk/layout.h), because this is easy to
  integrate with many keyboards
  (tested with [Ferris Sweep](https://keebmaker.com/products/ferris-sweep)).
- Use [US international keyboard layout with dead keys](https://dry.sailingissues.com/us-international-keyboard-layout.html) for German umlauts.
- [TODos](TODO.md)

## Modifiers

For the base layer, there are several options for modifiers for different typing speeds and personal preferences:

- One shot mods with a low timeout - so they don't interfere with typing fast
- Thumb combos - they can be pressed more easily compared to the one shot mods - if you want slower typing speed
- Mod layer - an extra layer with home row mods, which are even easier to press than the thumb combos

Then you can also enable Home row mods "Layer Options" (currently disabled, because of timing issues).

You can edit the tables to adjust the modifiers to your needs.

### One Shot Mods

Remove this table to disable one shot mods.

| Base Layer One Shot Mod Combos | Fingers             | Timeout Delta |
|--------------------------------|---------------------|:-------------:|
| Timeout                        | 12                  |               |
| Shift                          | Ring, Middle        |               |
| Ctrl                           | Middle, Index       |               |
| Alt                            | Ring, Index         |      10       |

more combos if you want to use the pinky finger for mods:

| Ctrl-Shift                     | Middle, Lower Index |      20       |
| Alt-Shift                      | Ring, Lower Index   |      20       |
| Ctrl-Alt                       | Pinky, Lower Index  |      20       |
| Ctrl-Alt-Shift                 | Pinky, Middle       |      30       |

### Thumb Combos

- The thumb is defined in the layout table below as "HomeRowThumbCombo".
- The other fingers are the same as in the table above.

Remove this table to disable thumb combos.

| Base Layer Thumb Mod Combos | Fingers             |
|-----------------------------|---------------------|
| Timeout                     | 35                  |

### Mod Layer

The Mod layer is defined in the Layer table below as "Mod".

### Home Row Mods

On all other layers, the modifiers are as shown in the table below.

| Home Row Modifiers | Finger |
|--------------------|--------|
| Alt                | Ring   |
| Shift              | Middle |
| Ctrl               | Index  |

## Layout

How to read this layout:

- 💎 = combo key (e.g. middle and index finger in top row pressed together produce "b")
- 🪜 = transparent key
- ❌ = key can't be used because the layer was activated with that key
- empty = use key from base layer
- FnBra = capitalized words are layer names - if they are a key, the layer is activated while the key is held
- /+Ctrl = tab-mod - / on tab and CTRL on hold
- =Mouse = set mouse layer active/inactive on tap (toggle)
- C-w = Ctrl-w (same for Alt and Shift)
- "that" = combo that produces "that"
- 80 custom timeout for combo
- The symbol table at the bottom shows the meaning of the symbols used in the layout.

> **Note**: The layout is generated from this file directly.

| Layer  | L. Pin. | L. Ring | L. Mid. | L. Ind. | R. Ind. | R. Mid. | R. Ring | R. Pin. |
|:------:|:-------:|:-------:|:-------:|:-------:|:-------:|:-------:|:-------:|:-------:|
|  Base  | sw-hand |    w    |    m    |    f    |    y    |    o    |    u    |   esc   |
|  Base  |    r    |    s    |    t    |    h    |    n    |    a    |    i    |    l    |
|  Base  |    b    |    c    |    g    |    d    |    p    |   rep   |    ,    |    .    |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |         |         |   💎    |    z    |         |    ö    |    ü    |         |
|  Base  |         |         |         |         |         |    ä    |         |    ß    |
|  Base  |         |         |         |         |         |         |         |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |         |   💎    |    v    |   f4    |         |         |         |         |
|  Base  |         |         |         |         |         |         |         |         |
|  Base  |         |         |         |         |         |         |         |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |         |         |         |         |         |         |         |         |
|  Base  |         |         |         |         |         |         |         |         |
|  Base  |         |         |   💎    |  k 120  |   esc   |   💎    |         |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |         |         |         |         |         |         |         |         |
|  Base  |         |         |         |         |         |         |         |         |
|  Base  |         |   💎    |    j    |    x    |  O-🖱️  |  capsW  |   💎    |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |         |         |         |         |         |         |         |         |
|  Base  |         |         |         |         |         |         |         |         |
|  Base  |   💎    |    q    |         |         |         |         |   ↩️️   |   💎    |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |         |         |         |  "for"  |  "ny"   |         |         |         |
|  Base  |         |         |         |   💎    |   💎    |         |         |         |
|  Base  |         |         |         |  "qu"   |  "ent"  |         |         |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |         |         | "the "  |         |         |  "oa"   |         |         |
|  Base  |         |         |   💎    |         |         |   💎    |         |         |
|  Base  |         |         | "and "  |         |         |  "ing"  |         |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |         |  "ws"   |         |         |         |         |  "ui"   |         |
|  Base  |         |   💎    |         |         |         |         |   💎    |         |
|  Base  |         |  "sc"   |         |         |         |         |  "ion"  |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Base  |  "tio"  |         |         |         |         |         |         |  "l."   |
|  Base  |   💎    |         |         |         |         |         |         |   💎    |
|  Base  |  "br"   |         |         |         |         |         |         |  .spc   |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| TabNav |         |   C-w   |  undo   |  redo   |   esc   |   tab   |   ins   |         |
| TabNav |  Nav2   | tab ➡️  | win ➡️  |  S-tab  |   ⬅️    |   ⬆️    |   ⬇️    |   ➡️    |
| TabNav | S-paste |  copy   |  paste  |   cut   |   ↩️️   |  bspc   |   del   |   spc   |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|  Nav2  |         |         |         |         |         |         |         |         |
|  Nav2  |    ❌    |         |         |         |  ⬅️⬅️   |  ⬆️⬆️   |  ⬇️⬇️   |  ➡️➡️   |
|  Nav2  |         |         |         |         |         |         |         |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| NumCur |         |    6    |    5    |    4    |  pipe   |    _    |    +    |         |
| NumCur |    3    |    2    |    1    |    0    |    "    |    {    |    }    |    :    |
| NumCur |         |    9    |    8    |    7    |    ~    |    &    |    !    |    ?    |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| Mouse  |         |  🖱️3   |  🖱️2   |         |         |  🖱️2   |  🖱️3   |         |
| Mouse  | Mouse2  | KC_ACL0 | KC_ACL1 | KC_ACL2 |  🖱️⬅️  |  🖱️⬆️  |  🖱️⬇️  |  🖱️➡️  |
| Mouse  |         |         | =Mouse  |  🖱️1   |  🖱️1   | =Mouse  |         |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| Mouse2 |         |         |         |         |         |         |         |         |
| Mouse2 |    ❌    |         |         |         | 🖲️️⬅️  | 🖲️️⬆️  | 🖲️️⬇️  | 🖲️️➡️  |
| Mouse2 |         |         |         |         |         |         |         |         |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| ParBra |         |    #    |    *    |         |    \    |    -    |    =    |         |
| ParBra |    $    |    (    |    )    |    @    |    '    |    [    |    ]    |    ;    |
| ParBra |    %    |    <    |    >    |   win   |    `    |  aRep   |    ^    |    /    |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|   Fn   |         |   f6    |   f5    |   f4    |         | =Mouse  |  A-f7   |         |
|   Fn   |   f3    |   f2    |   f1    |   f10   |  OSM-C  |  OSM-S  |  OSM-A  |  OSM-G  |
|   Fn   |   f11   |   f9    |   f8    |   f7    |         |         |         |  Media  |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| Media  | DT_PRNT |   🔅    |   🔆    |         |         |   🔇    |         |         |
| Media  |  DT_UP  |   🔈    |   🔊    |         |   prt   |         |         |         |
| Media  | DT_DOWN |   ⏮️    |   ⏭️    |   ⏯️    |   🔇    |   🔈    |   🔊    |    ❌    |
|        | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |

| Thumb  | Left Inner |        Left Outer        |      Right Outer       | Right Inner |
|:------:|:----------:|:------------------------:|:----------------------:|:-----------:|
|  Base  | tab+TabNav | ComboLayer:ParBra,30 spc | ComboLayer:NumCur,30 e |   bspc+Fn   |
|        | ---------- | ------------------------ | ---------------------- | ----------- |
|  Base  |     💎     |                          |                        | NumBra 100  |
|        | ---------- | ------------------------ | ---------------------- | ----------- |
| TabNav |     ❌      |            ❌             |          ↩️️           |   NumBra    |
|        | ---------- | ------------------------ | ---------------------- | ----------- |
|   Fn   |   NumBra   |           f12            |           ❌            |      ❌      |
|        | ---------- | ------------------------ | ---------------------- | ----------- |

## Options

A note on layer names:

Par = Parentheses        (
Cur = Curly Parentheses  {
Bra = Brackets           [

| LayerOptions | Modifiers Left | Modifiers Right | Fallback Left | Fallback Right | Flags  |
|:------------:|:--------------:|:---------------:|:-------------:|:--------------:|--------|
|     Base     |                |                 |               |                |        |
|    TabNav    |    HomeRow     |                 |               |                |        |
|     Nav2     |    HomeRow     |                 |               |                |        |
|      Fn      |                |                 |               |                |        |
|    NumBra    |    HomeRow     |     HomeRow     |    NumCur     |     ParBra     |        |
|    NumCur    |                |                 |               |                | Hidden |
|    ParBra    |                |                 |               |                | Hidden |
|    Mouse     |    HomeRow     |                 |               |                |        |
|    Mouse2    |    HomeRow     |                 |               |                |        |
|    Media     |                |                 |               |                |        |

| Symbol  | Command                |
|---------|------------------------|
| ⬅️      | KC_LEFT                |
| ⬅️⬅️    | KC_HOME                |
| ⬇️      | KC_DOWN                |
| ⬇️⬇️    | KC_PGDN                |
| ⬆️      | KC_UP                  |
| ⬆️⬆️    | KC_PGUP                |
| ➡️      | KC_RIGHT               |
| ➡️➡️    | KC_END                 |
| ins     | KC_INS                 |
| del     | KC_DEL                 |
| spc     | KC_SPC                 |
| esc     | KC_ESC                 |
| ↩️️     | KC_ENT                 |
| bspc    | KC_BSPC                |
| win     | KC_LGUI                |
| alt     | KC_LALT                |
| tab     | KC_TAB                 |
| S-tab   | S(KC_TAB)              |
| prt     | KC_PSCR                |
| pipe    | KC_PIPE                |
| 🖱️⬅️   | KC_MS_L                |
| 🖱️⬇️   | KC_MS_D                |
| 🖱️⬆️   | KC_MS_U                |
| 🖱️➡️   | KC_MS_R                |
| 🖲️️⬅️  | KC_WH_L                |
| 🖲️️⬇️  | KC_WH_D                |
| 🖲️️⬆️  | KC_WH_U                |
| 🖲️️➡️  | KC_WH_R                |
| 🖱️1    | KC_BTN1                |
| 🖱️2    | KC_BTN2                |
| 🖱️3    | KC_BTN3                |
| 🔇      | KC_MUTE                |
| ⏯️      | KC_MPLY                |
| ⏮️      | KC_MPRV                |
| ⏭️      | KC_MNXT                |
| 🔊      | KC_VOLU                |
| 🔈      | KC_VOLD                |
| 🔆      | KC_BRIU                |
| 🔅      | KC_BRID                |
| redo    | RCS(KC_Z)              |
| undo    | C(KC_Z)                |
| ctl     | KC_LCTL                |
| S-paste | RCS(KC_V)              |
| cut     | C(KC_X)                |
| copy    | C(KC_C)                |
| paste   | C(KC_V)                |
| ä       | ALGR(KC_Q)             |
| ö       | ALGR(KC_P)             |
| ü       | ALGR(KC_Y)             |
| ß       | ALGR(KC_S)             |
| '       | ALGR(KC_QUOTE)         |
| "       | ALGR(KC_DQUO)          |
| `       | ALGR(KC_GRV)           |
| ~       | ALGR(KC_TILD)          |
| ^       | ALGR(KC_CIRC)          |
| 🪜      | KC_TRNS                |
| rep     | QK_REPEAT_KEY          |
| aRep    | QK_ALT_REPEAT_KEY      |
| capsW   | QK_CAPS_WORD_TOGGLE    |
| tab ➡️  | custom:NEXT_TAB        |
| win ➡️  | custom:NEXT_WINDOW     |
| NumBra  | custom:NUMBRA          |
| O-🖱️   | custom:ONE_SHOT_MOUSE  |
| .spc    | custom:DOT_SPC         |
| OSM-A   | OSM(MOD_LALT)          |
| OSM-S   | OSM(MOD_LSFT)          |
| OSM-C   | OSM(MOD_LCTL)          |
| OSM-G   | OSM(MOD_LGUI)          |
| sw-hand | QK_SWAP_HANDS_ONE_SHOT |



## Generator

- should be usable with any keyboard layout

qmk flash -kb ferris/sweep -km zeitlinger -bl avrdude-split-left
