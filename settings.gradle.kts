pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            setUrl("https://jitpack.io")
        }
        //noinspection JcenterRepositoryObsolete
        jcenter(){
            content{
                includeModule("com.theartofdev.edmodo","android-image-cropper")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
        //noinspection JcenterRepositoryObsolete
        jcenter(){
            content{
                includeModule("com.theartofdev.edmodo","android-image-cropper")
            }
        }
    }
}

rootProject.name = "My Application"
include(":app")
