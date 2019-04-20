# CSC690 Readme

# Requirements

In order to build and run this project, you need to download and setup the following:

- IntelliJ IDEA
    - You can download it from https://www.jetbrains.com/idea/download/
- SonarQube Developer Edition
    - This is NOT free. You can buy it from https://www.sonarsource.com/plans-and-pricing/
    - The Community edition cannot be used with the project, as it does not support Swift scanning.
    - You can download it from https://www.sonarqube.org/downloads/
- SonarQube Scanner
    - You can download it from https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner
- GitHub Account
    - You can create one at https://github.com/
    - You need to set up a Personal Access Token for this project, and put the personal access token in conf.kt, along with your username.
        - You can find how to set it up here: https://help.github.com/en/articles/creating-a-personal-access-token-for-the-command-line
# Execution Instruction Summary
- Fill your GitHub credentials in conf.kt
- Run downloader/main.kt
- Start SonarQube server
- Enter SonarQube Developer Edition license
- Run scanner/main.kt
- Run preprocessor/main.kt
- Run analyzer/MyApp.kt
# About conf.kt

The file conf.kt contains variables that needs to be correctly set before the project can be run:

- GITHUB_USERNAME and GITHUB_PERSONAL_ACCESS_TOKEN
    - Your GitHub Username and GitHub Personal Access Token for this project. These credentials are required by GitHub to download large amounts of files.
- SONAR_SCANNER_BIN_PATH
    - Path to the bin folder of Sonar Scanner
# About This Project

This project consists of four consecutive parts:

- Downloader
- Scanner
- Preprocessor
- Analyzer

The downloader will clone repositories from GitHub. The scanner will tell SonarQube to perform static analysis on the downloaded repositories. The preprocessor reads data from SonarQube’s analysis results, and streamline the data for our analysis. The analyzer analyzes the streamlined data, and shows the analysis results.

They have to be run in consecutive order. The Analyzer will not run unless there’s preprocessed data from the Preprocessor. The Preprocessor will not run unless SonarQube is running, and has completed static analysis, as told by the Scanner. The Scanner will not run unless there are repositories in the REPO_PATH.

# About the Scanner

The scanner will build the repositories downloaded by the downloader, and tell SonarQube to scan the repositories. Due to varying build processes of different project types, the batch building process is not language agnostic at all, and have varying degrees of success even for Kotlin and Swift projects. The log files contains which projects are successfully built.


