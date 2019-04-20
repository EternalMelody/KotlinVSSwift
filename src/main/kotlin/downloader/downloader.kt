package downloader

import GITHUB_PERSONAL_ACCESS_TOKEN
import GITHUB_USERNAME
import REPO_COUNT
import REPO_PATH
import org.apache.log4j.BasicConfigurator
import org.eclipse.egit.github.core.client.GitHubClient
import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.jgit.api.Git
import java.io.File

fun main(args: Array<String>) {
    BasicConfigurator.configure()
    downloadRepos("kotlin")
    downloadRepos("swift")
}

fun downloadRepos(language:String){
    val client = GitHubClient()

    client.setCredentials(GITHUB_USERNAME, GITHUB_PERSONAL_ACCESS_TOKEN)

    val service = RepositoryService(client)
    val repos = service.searchRepositories("sort:stars", language)

    repos.filterIndexed { index, _ -> index< REPO_COUNT }.forEach {
        val repo = service.getRepository(it.owner, it.name)
        Git.cloneRepository()
            .setURI(repo.cloneUrl)
            .setDirectory(File("$REPO_PATH/${language}_repos/${it.name}"))
            .call();
    }
}