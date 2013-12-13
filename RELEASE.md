
# Steps to release to melpa.  

I do not think I can use the maven release plugin as the artifact is
being written to a new branch.

##  Create a release branch:

```
       git branch <RELEASE>
       git push origin <RELEASE>
       git checkout <RELEASE>
```

## Edit the pom.xml on the branch with the new VERSION

##  Create the dist in the RELEASE branch

```
        mvn clean package
        mkdir dist
        cd dist
        unzip ../target/*.zip
        cd ..
        git add dist
        git commit -a -m "<RELEASE>"
	git push
```

## Update the MELPA recipe to match

```
  	(malabar-mode 
		      :repo "m0smith/malabar-mode" 
		      :fetcher github 
		      :commit "origin/<RELEASE>"
		      :files (
		      	     ("." "dist/malabar-<VERSION>/lisp/*")
			     ("lib" "dist/malabar-<VERSION>/lib/*")
			     ))
```

## Test the install from the melpa project dir and then test in emacs

```
        rm -rf ~/.emacs.d/elpa/malabar-mode-*
        make clean
        make recipes/malabar-mode
        emacs --batch --exec '(package-install-file "/full/path/to/melpa/packages/malabar-mode-<MELPA-VERSION>.tar")'
```



## Commit MELPA branch and make pull request

##  Switch back the malabar-mode master branch

```
  	 git checkout master
```
##  Update the pom.xml version to start the next development cycle

