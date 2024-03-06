import React, { useState } from 'react'
import { StyleSheet, Text, View, StatusBar, Keyboard, TouchableWithoutFeedback, FlatList } from 'react-native';
import colors from '../resources/colors';
import RoundIconBtn from '../components/RoundIconBtn';
import TaskInputModal from '../components/TaskInputModal';
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useTasks } from '../context/TaskProvider';
import Task from '../components/Task';

function MainScreen({navigation}) {
    const [modalVisible, setModalVisible] = useState(false);
    const {tasks, setTasks} = useTasks();

    const handleOnSubmit = async(title, priority, descr) => {
        const task = {id: Date.now(), title, priority, descr, time: Date.now()};
        const updatedTasks = [...tasks, task];
        setTasks(updatedTasks);
        await AsyncStorage.setItem('tasks', JSON.stringify(updatedTasks));
    };

    const openTask = (task) => {
        navigation.navigate('TaskDetails', { task });
    }
    
    return (
        <>
            <StatusBar barStyle='dark-content' backgroundColor={colors.LIGHT}/>
            <View style={styles.container}>
                <FlatList 
                    data={tasks} 
                    numColumns={2}
                    columnWrapperStyle={{ 
                        justifyContent: 'space-between',
                        marginBottom: 15
                    }}
                    keyExtractor={item => item.id.toString()}
                    renderItem={({item}) => <Task onPress={() => openTask(item)} item={item}/>}
                />
                {!tasks.length ? 
                    <View style={[StyleSheet.absoluteFillObject, styles.emptyHeaderContainer]}>
                        <Text style={styles.emptyHeader}>Add Tasks</Text>
                    </View> 
                : null}
            </View>
            <RoundIconBtn 
                onPress={() => setModalVisible(true)}   
                antIconName='plus' 
                style={styles.addBtn}
            />
            <TaskInputModal 
                visible={modalVisible} 
                onClose={() => setModalVisible(false)} 
                onSubmit={handleOnSubmit}/>
        </>
    )
}

const styles = StyleSheet.create({
    container: {
        paddingHorizontal: 20,
        flex: 1,
        zIndex: 1,
        marginTop: 20
    },
    emptyHeader: {
        fontSize: 30,
        fontWeight: 'bold',
        opacity: 0.5,
        textTransform: 'uppercase'
    },
    emptyHeaderContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: -1
    },
    addBtn: {
        position: 'absolute',
        right: 15,
        bottom: 50,
        zIndex: 1
    }
})
export default MainScreen
