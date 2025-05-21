// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorCollectData",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorCollectData",
            targets: ["CollectDataPluginPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "CollectDataPluginPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CollectDataPluginPlugin"),
        .testTarget(
            name: "CollectDataPluginPluginTests",
            dependencies: ["CollectDataPluginPlugin"],
            path: "ios/Tests/CollectDataPluginPluginTests")
    ]
)