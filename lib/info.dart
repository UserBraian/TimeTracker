import 'package:intl/intl.dart';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'tree.dart';
import 'PageActivities.dart';

class PageInfor extends StatefulWidget {
  final int id;
  final String name;
  PageInfor(this.id, this.name);

  @override
  _PageInforState createState() => _PageInforState();
}

class _PageInforState extends State<PageInfor> {
  late Tree tree;
  late int id;

  @override
  void initState() {
    super.initState();
    id = widget.id;
    tree = getTree(id);
  }

  Widget build(BuildContext context) {
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
                } // TODO go home page = root
            ),
            //TODO other actions
          ],
        ),
        body: Container(
            alignment: Alignment.center,
            padding: const EdgeInsets.all(30),
            child: Column(
                children: <Widget>[
                  Row(
                      children: <Widget>[Text('Nombre: ${tree.root.name}')]
                  ),
                  Row(
                      children: <Widget>[Text('Fecha Inicial: ${tree.root.initialDate}')]
                  ),
                  Row(
                      children: <Widget>[Text('Fecha Final: ${tree.root.finalDate}')]
                  ),
                  Row(
                      children: <Widget>[Text('Duracion: ${tree.root.duration}')]
                  ),
                  Row(
                      children: <Widget>[
                        IconButton(icon: Icon(Icons.delete), onPressed:(){/*delete activity*/}),
                        Text(' '),
                        IconButton(icon: Icon(Icons.edit), onPressed:(){_openPopup(context);})]

                  )
                ]
            )
        )
    );
  }
  void _openPopup(context) {
    //Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) => PageFormulario(id, name)));
    showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: const Text("Edit Information"),
        //content: const Text("You have raised a Alert Dialog Box"),
        actions: <Widget>[
          TextButton(
            onPressed: () {
              Navigator.of(ctx).pop();
            },
            child: Container(
              color: Colors.grey,
              padding: const EdgeInsets.all(14),
              child: const Text("okay"),
            ),
          ),
        ],
      ),
    );
  }
}