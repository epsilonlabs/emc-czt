Epsilon-Z Integration
===========

Eclipse plugins that extend Epsilon's Model Connectivity (EMC) layer with support for managing Z models using languages of the [Epsilon platform](http://www.eclipse.org/epsilon) to perform activities such as code generation, model validation and model-to-model transformation. The Z EMC driver supports both loading/querying and modifying/saving .zed models. The driver is a wrapper for the [CZT](http://czt.sourceforge.net/) parser/serialiser and works with CZT-compatible LaTeX models.

Example
-----------
The following EOL snippet demonstrates printing the names of all schemas in a Z model.
```javascript
for (para in AxPara.all.select(para|para.box = Box#SchBox)) {
  para.schText.declList.decl.name.word.first().println();
}
```

How to run
-----------
* [Download](https://eclipse.org/epsilon/download) an Epsilon distribution
* Clone this Git repository and import the following projects into your Eclipse workspace
	* org.eclipse.epsilon.emc.czt
	* org.eclipse.epsilon.emc.czt.dt
* Run a nested Eclipse instance
* In the new Eclipse instance import the org.eclipse.epsilon.emc.czt.examples project
* Ensure that the default encoding in your workspace is UTF-8 (through Preferences->General->Workspace)
* Try the examples by right-clicking and running the stored .launch configurations

Tips
-----------
* To explore the exact version of the metamodel that Z models conform to, open Epsilon's [EPackage Registry view](https://www.eclipse.org/epsilon/doc/articles/epackage-registry-view/), click the refresh button and look for the net.sourceforge.czt.zml metamodel.
* You can open a .zed LaTeX file using a metamodel-aware tree editor by right-clicking and then selecting Open with -> Other -> Exeed editor
* You can use [Exeed's facilties](https://www.eclipse.org/epsilon/doc/articles/inspect-models-exeed/) to inspect the structure of your model
