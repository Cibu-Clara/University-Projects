import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import MainScreen from './app/screens/MainScreen';
import TaskDetails from './app/components/TaskDetails';
import TaskProvider from './app/context/TaskProvider';

const Stack = createNativeStackNavigator();

export default function App() {
  return (
  <NavigationContainer>
    <TaskProvider>   
      <Stack.Navigator screenOptions={{headerTitle: '', headerTransparent: true}}>
        <Stack.Screen name="MainScreen">{props => <MainScreen {...props} />}</Stack.Screen>
        <Stack.Screen component={TaskDetails} name="TaskDetails" />
      </Stack.Navigator>
    </TaskProvider>
  </NavigationContainer>
  );
}