package downloader

import org.apache.log4j.BasicConfigurator
import org.eclipse.egit.github.core.client.GitHubClient
import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.jgit.api.Git
import java.io.File

fun main() {
    BasicConfigurator.configure()
    downloadRepos("kotlin")
    downloadRepos("swift")
}

fun downloadRepos(language:String){
    val client = GitHubClient()
    client.setCredentials("EternalMelody", "1ca6284b90fc1e7384faa90240f8fa8e5bf9cded")

    val service = RepositoryService(client)
    val repos = service.searchRepositories("sort:stars", language)
    repos.forEach {
        val repo = service.getRepository(it.owner, it.name)
        Git.cloneRepository()
            .setURI(repo.cloneUrl)
            .setDirectory(File("./repos/$language/${it.name}"))
            .call();
    }
}