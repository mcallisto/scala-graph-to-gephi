# Graph4Scala graphs to Gephi images
Basic conversion tool to visualize Graph4Scala graphs with the Gephi toolkit.

## Bridging two worlds
Graphs are seamless Scala collections thanks to the excellent [Graph for Scala](https://scala-graph.org/) library by Peter Empen.
Sometimes is needed to quickly visualize them.
[Gephi](https://github.com/gephi/gephi) is an award-winning open-source platform for visualizing and manipulating large graphs, with a standalone [toolkit](https://github.com/gephi/gephi-toolkit)

## How to

### Test
The library is built with [Mill](http://www.lihaoyi.com/mill).

1. [Install](http://www.lihaoyi.com/mill/#installation) Mill
2. Open a terminal and `cd` to the repo directory
3. Use the `mill jvm.test` command to run all tests
4. Or use the `mill jvm.test.one [testClassName]` command to run a single test

### Draw a graph
Add the `Drawable` trait to your project and use the `makeImage(g: Graph[N, E], path: String, name: String)` method to draw a PNG image file.
![directed graph image](docs/directed.png)