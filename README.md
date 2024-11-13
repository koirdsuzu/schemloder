# SchematicPlugin

`SchematicPlugin`は、Minecraft内で建築物や構造物の`.schem`ファイルを簡単に読み込み、保存、コピー、貼り付けするためのプラグインです。プレイヤーは、`WorldEdit`を使用して選択したエリアを`.schem`ファイルとして保存し、いつでも別の場所に貼り付けられます。

## 要件
- Minecraft Java Edition 1.21
- [WorldEdit](https://enginehub.org/worldedit/) プラグイン (v7.2.8 以上)

## インストール

1. [WorldEdit](https://enginehub.org/worldedit/) プラグインをダウンロードし、サーバーの`plugins`フォルダに配置します。
2. このプラグイン（`SchematicPlugin.jar`）を`plugins`フォルダにコピーします。
3. サーバーを再起動またはリロードします。

## 使い方

### 基本コマンド
このプラグインは、4つのコマンドを提供します。

- **/schematic load `<ファイル名>`**  
  指定したファイル名の`.schem`ファイルをロードします。ロードした内容は後で`paste`コマンドで貼り付けられます。
  
  例: `/schematic load mybuild`

- **/schematic save `<ファイル名>`**  
  現在の`WorldEdit`の選択範囲を、指定したファイル名で`.schem`ファイルとして保存します。
  
  例: `/schematic save mybuild`

- **/schematic paste**  
  最後に`load`または`copy`したクリップボードの内容を、現在のプレイヤーの位置に貼り付けます。

- **/schematic copy**  
  現在の`WorldEdit`の選択範囲をコピーし、貼り付け可能な状態にします。

### コマンドの例

1. **建築物の保存**  
   1. `//wand`コマンドを使用して、WorldEditの選択ツールを取得します。
   2. 保存したい建築物を選択します。
   3. `/schematic save <ファイル名>`コマンドを実行して、選択したエリアを`.schem`ファイルとして保存します。

2. **保存した建築物の読み込みと貼り付け**  
   1. `/schematic load <ファイル名>`コマンドを実行して、貼り付けたい`.schem`ファイルを読み込みます。
   2. 貼り付けたい場所に移動します。
   3. `/schematic paste`コマンドを実行して、指定した位置に建築物を貼り付けます。

3. **現在の選択範囲をコピーして貼り付ける**  
   1. `//wand`コマンドで選択ツールを取得し、コピーしたいエリアを選択します。
   2. `/schematic copy`コマンドを実行して、選択したエリアをコピーします。
   3. 貼り付けたい場所に移動し、`/schematic paste`コマンドを使用してコピー内容を貼り付けます。

## 注意
- `.schem`ファイルは、`plugins/SchematicPlugin`フォルダに保存されます。
- 大きな構造物を貼り付けるときは、サーバーのパフォーマンスに注意してください。Fast Async WorldEdit (FAWE) を併用すると負荷が軽減される場合があります。

## ライセンス
このプラグインはMITライセンスのもとで提供されます。
