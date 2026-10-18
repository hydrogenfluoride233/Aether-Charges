# Aether Charges / 天境结晶弹

## 内容

| 物品 | 弹体 | 效果 |
|---|---|---|
| 冰霜弹 | 冰结球 | 7 点伤害并减速；命中烈阳巨灵时可以把它冻住 |
| 炽炎弹 | 滚炎球 | 15 点伤害并点燃目标 |
| 阳雷弹 | 风暴雷球 | 5 点伤害，落点召下一道闪电 |
| 流云弹 | 境云冰晶 | 不造成伤害，沿弹道把目标推开 |

结晶弹是一次性的，便宜、能连发。对应的**权杖**不消耗材料、可以反复使用，代价是更长的冷却，而且不能合成——只在青铜、白银、黄金三座地牢的箱子里。

伤害、冷却、弹速、是否落雷都在 `config/crystal_charges-common.toml` 里。

## 依赖

- Minecraft 1.21.1 / NeoForge 21.1
- The Aether 1.21.1-1.5.x

物品的 lore 是普通语言键，直接进天境自带的指南书，不需要额外装库。

## 构建

```
# 1. 把天境的 jar 放进 libs/（只用于编译，不随包分发）
#    libs/aether-1.21.1-1.5.10-neoforge.jar

# 2. 构建
./gradlew build
```

产物在 `build/libs/`。`libs/` 已写进 `.gitignore`：天境的 jar 只在编译时需要。

## 材质

现在八件物品的图标都是程序生成的占位图，`preview/` 里有动画试验（菱形与球状各一版）。正式贴图还没画，见 [issue #1](https://github.com/hydrogenfluoride233/Aether-Charges/issues/1)。

## 致谢

- The Aether Team：晶体实体、模型与射弹贴图都是运行期直接复用天境的东西

## 许可

MIT
