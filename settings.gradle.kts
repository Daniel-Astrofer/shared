rootProject.name = "kerosene-shared"

val contractsDirectory = providers.environmentVariable("KEROSENE_CONTRACTS_DIR")
    .orNull
    ?.takeIf { it.isNotBlank() }
    ?: sequenceOf(
        "../contracts",
        "../kerosene-contracts",
    ).firstOrNull { file(it).isDirectory }
    ?: "../contracts"

includeBuild(contractsDirectory) {
    dependencySubstitution {
        substitute(module("io.kerosene.contracts:kerosene-contracts"))
            .using(project(":"))
    }
}
