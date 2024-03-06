import React, { useState } from 'react';
import { StyleSheet, Text, ScrollView, View, Alert } from 'react-native';
import { useHeaderHeight } from '@react-navigation/elements';
import colors from '../resources/colors';
import RoundIconBtn from './RoundIconBtn';
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useTasks } from '../context/TaskProvider';
import TaskInputModal from './TaskInputModal';


function TaskDetails(props) {
    const [task, setTask] = useState(props.route.params.task);
    const headerHeight = useHeaderHeight();
    const {setTasks} = useTasks();
    const [showModal, setShowModal] = useState(false);
    const [isEdit, setIsEdit] = useState(false);

    const deleteTask = async() => {
        const result = await AsyncStorage.getItem('tasks');
        let tasks = [];
        if (result !== null) tasks = JSON.parse(result);

        const taskIndex = tasks.findIndex((t) => t.id === task.id);

        if (taskIndex !== -1) {
            const newTasks = [...tasks];
            newTasks.splice(taskIndex, 1);
        setTasks(newTasks);
        await AsyncStorage.setItem('tasks', JSON.stringify(newTasks));
        props.navigation.goBack();
        };
    };

    const displayDeleteAlert = () => {
        Alert.alert('Confirmation', 'Are you sure you want to delete this task?', [
            {
                text: 'Yes',
                onPress: deleteTask
            },  
            {
                text: 'No',
                onPress: () => console.log('no thx')
            },
        ], {
            cancelable: true
        })
    }

    const handleUpdate = async(title, priority, descr, time) => {
        const result = await AsyncStorage.getItem('tasks');
        let tasks = [];
        if (result !== null) tasks = JSON.parse(result);

        const newTasks = tasks.filter(t => {
            if(t.id === task.id) {
                t.title = title;
                t.priority = priority;
                t.descr = descr;
                t.isUpdated = true;
                t.time = time;

                setTask(t);
            }
            return t;
        })

        setTasks(newTasks);
        await AsyncStorage.setItem('tasks', JSON.stringify(newTasks));
    }
    const handleOnClose = () => setShowModal(false);

    const openEditModal = () => {
        setIsEdit(true);
        setShowModal(true);
    }

    const formatDate = (time) => {
        const date = new Date(time);
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        const hrs = date.getHours();
        const min = date.getMinutes();

        return `${day}/${month}/${year} at ${hrs}:${min}`;
    }

    return (
        <>
            <ScrollView contentContainerStyle={[styles.container, {paddingTop: headerHeight}]}>
                <Text style={styles.title}>{task.title}</Text>
                <Text style={styles.priority}>{`Priority level: ${task.priority}`}</Text>
                <Text style={styles.descr}>{`Description: ${task.descr}`}</Text>
            </ScrollView>
            <View style={styles.btnContainer}>
                <RoundIconBtn 
                    antIconName='delete' 
                    style={{ backgroundColor: colors.ERROR, marginBottom: 15 }} 
                    onPress={displayDeleteAlert}
                />
                <RoundIconBtn 
                    antIconName='edit'
                    onPress={openEditModal}
                />
            </View>
            <TaskInputModal isEdit={isEdit} task={task} onClose={handleOnClose} onSubmit={handleUpdate} visible={showModal}/>
        </>
    );
};

const styles = StyleSheet.create({
    container: {
        paddingHorizontal: 15
    },
    title: {
        fontSize: 30,
        color: colors.PRIMARY,
        fontWeight: 'bold'
    },
    priority: {
        fontSize: 20,
        opacity: 0.6
    },
    descr: {
        fontSize: 20,
        opacity: 0.6
    },
    time: {
        textAlign: 'right',
        fontSize: 12,
        opacity: 0.5
    },
    btnContainer: {
        position: 'absolute',
        right: 15,
        bottom: 50
    }
});

export default TaskDetails;