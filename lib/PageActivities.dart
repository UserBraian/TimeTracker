import 'package:intl/intl.dart';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'tree.dart';
import 'PageIntervals.dart';
import 'PageReport.dart';
import 'info.dart';
import 'creation.dart';
import 'requests.dart';

class PageActivities extends StatefulWidget {
  final int id;
  final String name;

  PageActivities(this.id, this.name);

  @override
  _PageActivitiesState createState() => _PageActivitiesState();
}

class _PageActivitiesState extends State<PageActivities> {
  late Future<Tree> tree;
  late int id;
  bool isPlayPressed = false;
  final DateFormat formatter = DateFormat('dd-MM-yy hh:mm:ss');
  

  @override
  void initState() {
    super.initState();
    id = widget.id;
    tree = getTree(id);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(future: tree, builder: (context, snapshot){
      if (snapshot.hasData) {
        final ButtonStyle style =
        ElevatedButton.styleFrom(textStyle: const TextStyle(fontSize: 20));//////////////temporal

        return Scaffold(
          backgroundColor: Colors.white,
          appBar: AppBar(
            title: Text(snapshot.data!.root.name),
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
            itemCount: snapshot.data!.root.children.length,
            itemBuilder: (BuildContext context, int index) =>
                Container(
                    decoration: BoxDecoration(
                      border: Border.all(
                        color: Colors.blueAccent,
                      ),
                      borderRadius: BorderRadius.all(Radius.circular(20)),
                    ),
                    child:_buildRow(snapshot.data!.root.children[index], index)),
            separatorBuilder: (BuildContext context, int index) => const Divider(),
          ),
          floatingActionButton: FloatingActionButton(
            onPressed: () {
              _navigateDownCreation(snapshot.data!.root);
            },
            backgroundColor: Colors.blue,
            child: const Icon(Icons.add),
          ),
        );
      }else if (snapshot.hasError) {
        return Text("${snapshot.error}");
      }
      return Container(
          height: MediaQuery.of(context).size.height,
          color: Colors.amber,
          child: const Center(
            child: CircularProgressIndicator(),
          ));
    });
  }

  Widget _buildRow(Activity activity, int index) {
    String strDuration = Duration(seconds: activity.duration).toString().split('.').first;
    // split by '.' and taking first element of resulting list
    // removes the microseconds part
    assert (activity is Project || activity is Task);
    if (activity is Project) {
      return ListTile(
        leading: IconButton(icon: Icon(Icons.info),color: Colors.indigo, onPressed:(){_navigateDownInfo(activity.id, activity.name);}),
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
        leading: IconButton(icon: Icon(Icons.info),color: Colors.indigo, onPressed:(){_navigateDownInfo(activity.id, activity.name);}),
        title: Row(mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text('${activity.name} '),
              IconButton(icon: (activity.active)?Icon(Icons.stop_circle_outlined):Icon(Icons.play_circle_outline_outlined,),
                  tooltip:(activity.active)?'Stop current interval':'Start new interval',
                  color: (activity.active)?Colors.red:Colors.green,
                  onPressed:(){
                    setState((){
                      if(activity.active==true){
                        activity.active=false;
                      }else{
                        activity.active=true;
                      }
                    });
                    print('pulsando');//Se muestra en consola
                    print(isPlayPressed); //se muestra en consola

                    if(activity.active==true){
                      //enviar peticion start?id
                      start(activity.id);
                    }else{
                      //enviar peticion stop?id
                      stop(activity.id);
                    }
                  }
              )
              ]
        ),
        trailing: trailing,
        onTap: () => _navigateDownIntervals(activity.id, activity.name),
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

  void _navigateDownIntervals(int id, String name) {
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageIntervals(id, name))
    );

  }
}