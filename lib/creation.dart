import 'package:intl/intl.dart';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'tree.dart';
import 'PageActivities.dart';
import 'requests.dart';

class PageCreation extends StatefulWidget {
  final Activity parent;
  PageCreation(this.parent);

  @override
  _PageCreationState createState() => _PageCreationState();
}

class _PageCreationState extends State<PageCreation> {
  late Activity parent;
  void initState() {
    super.initState();
    parent = widget.parent;
  }
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Creation ...'),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                while (Navigator.of(context).canPop()) {
                  Navigator.of(context).pop();
                }
                PageActivities(0, "root");
              }
          ),
          //TODO other actions
        ],
      ),
      body: PageForm(parent),
    );
  }
}

class PageForm extends StatefulWidget {
  final Activity parent;
  PageForm(this.parent);

  @override
  MyCustomForm createState() => MyCustomForm();
}

class MyCustomForm extends State<PageForm> {
  String formatValue = "Project";
  late Activity parent;
  final nombre = TextEditingController();
  final tags = TextEditingController();

  @override
  void initState() {
    super.initState();
    parent = widget.parent;
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Padding(
            padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 16),
            child: DropdownButton(
              items: <String>['Project', 'Task']
                  .map<DropdownMenuItem<String>>((String value) {
                return DropdownMenuItem<String>(
                  value: value,
                  child: Text(value),
                );
              }).toList(),
              value: formatValue,
              onChanged: (String? newValue) {
                setState(() {
                  formatValue = newValue!;
                });
              },
            )
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 16),
          child: TextFormField(
            controller: nombre,
            decoration: const InputDecoration(
              border: UnderlineInputBorder(),
              labelText: 'Nombre',
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 16),
          child: TextFormField(
            controller: tags,
            decoration: const InputDecoration(
              border: UnderlineInputBorder(),
              labelText: 'Tags( separados por comas sin espacios)',
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 16),
          child: TextButton(
            onPressed: () { add(nombre.text,parent.id,tags.text,formatValue);
              Navigator.of(context).pop();
              },
            child: Container(
              padding: const EdgeInsets.all(14),
              child: const Text("CREAR"),
            ),
          ),
          ),
      ],
    );
  }
}