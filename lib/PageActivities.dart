import 'package:intl/intl.dart';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'tree.dart';
import 'PageIntervals.dart';
import 'PageReport.dart';
import 'info.dart';
import 'creation.dart';
//import 'requests.dart';

class PageActivities extends StatefulWidget {
  final int id;
  final String name;

  PageActivities(this.id, this.name);

  @override
  _PageActivitiesState createState() => _PageActivitiesState();
}

class _PageActivitiesState extends State<PageActivities> {
  late Tree tree;
  late int id;
  bool isPlayPressed=false;///////////////////////////////////////   BOTON PLAY/STOP. /////////////////////////////////////////


  @override
  void initState() {
    super.initState();
    id = widget.id;
    tree = getTree(id);
  }

  @override
  Widget build(BuildContext context) {
    final ButtonStyle style =
    ElevatedButton.styleFrom(textStyle: const TextStyle(fontSize: 20));//////////////temporal

    return Scaffold(
      appBar: AppBar(
        title: Text(tree.root.name),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                while (Navigator.of(context).canPop()) {
                  Navigator.of(context).pop();
                }
                PageActivities(0, "root");
              }
          ),
          IconButton(icon: Icon(Icons.report),
              onPressed: () {
                Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) => PageReport(),
                ));
              }
          ),
        ],
      ),
      body: ListView.separated(
        // it's like ListView.builder() but better
        // because it includes a separator between items
        padding: const EdgeInsets.all(16.0),
        itemCount: tree.root.children.length,
        itemBuilder: (BuildContext context, int index) =>
            _buildRow(tree.root.children[index], index),
        separatorBuilder: (BuildContext context, int index) =>
        const Divider(),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _navigateDownCreation(tree.root);
        },
        backgroundColor: Colors.blue,
        child: const Icon(Icons.add),
        tooltip:'Create new project/task',
      ),
    );
  }

  Widget _buildRow(Activity activity, int index) {
    String strDuration = Duration(seconds: activity.duration).toString().split('.').first;
    // split by '.' and taking first element of resulting list
    // removes the microseconds part
    assert (activity is Project || activity is Task);
    if (activity is Project) {
      return ListTile(
        leading: IconButton(icon: Icon(Icons.info),tooltip: 'See information about', onPressed:(){_navigateDownInfo(activity.id, activity.name);}),
        title: Row(
            children: [
              Container(child: Text('${activity.name} ')),
              Image(height:30.0, image:NetworkImage('https://cdn-icons-png.flaticon.com/512/8291/8291136.png'))
            ]
        ),
        //Container(child: <Widget>[Text('${activity.name}')]),
        //         Image(image:NetworkImage('https://cdn-icons-png.flaticon.com/512/8291/8291136.png'))),

        //Image(image:NetworkImage('https://cdn-icons-png.flaticon.com/512/8291/8291136.png')),),

        trailing: Text('$strDuration'),
        onTap: () => _navigateDownP(activity.id, activity.name),
        // TODO, navigate down to show children tasks and projectsw
      );
    } else {
      Task task = activity as Task;
      Widget trailing;
      trailing = Text('$strDuration');
      return ListTile(
        leading: IconButton(icon: Icon(Icons.info),tooltip:'See information about', onPressed:(){_navigateDownInfo(activity.id, activity.name);}),
        title: Row(mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text('${activity.name} '),
              IconButton(icon: (isPlayPressed)?Icon(Icons.stop_circle_outlined):Icon(Icons.play_circle_outline_outlined,),
                     tooltip:(isPlayPressed)?'Stop current interval':'Start new interval',
                     onPressed:(){
                       setState((){
                         if(isPlayPressed==true){
                           isPlayPressed=false;
                         }else{
                           isPlayPressed=true;
                         }
                       });
                       print('pulsando');//Se muestra en consola
                       print(isPlayPressed); //se muestra en consola
                       
                       if(isPlayPressed==true){
                           //enviar peticion start?id
                         }else{
                           //enviar peticion stop?id
                         }
                     }
                    )
            ]
        ),
        trailing: trailing,
        onTap: () => _navigateDownIntervals(index),
        // TODO, navigate down to show intervals
        onLongPress: () {},
        // TODO start/stop counting the time for this task
      );
    }
  }

  void _navigateDownCreation(Activity parent) {
    Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) => PageCreation(parent)));
  }

  void _navigateDownInfo(int id, String name) {
    Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) => PageInfor(id, name)));
  }

  void _navigateDownP(int id, String name) {
    Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) => PageActivities(id, name)));
  }

  void _navigateDownIntervals(int childId) {
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageIntervals())
    );

  }
}
