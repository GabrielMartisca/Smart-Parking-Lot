import './App.css'

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Homepage from './Homepage'
import LoginPage from './pages/LoginPage'
import SignupPage from './pages/SignupPage'
import ForgotPasswordPage from './pages/ForgotPasswordPage'
import ProfileAdminPage from './pages/ProfileAdminPage';
import AdminParkingPage from './pages/AdminParkingPage';
import AdminPanelPage from './pages/AdminPanelPage';
import AddParkingLotPage from './pages/AddParkingLotPage';
import EditParkingLotPage from './pages/EditParkingLotPage';
import AccountBalance from './pages/AccountBalance';
import Profiles from './pages/Profiles';
import Details from './pages/Details';
import Cars from './pages/Cars';
import Messages from './pages/Messages';
import SettingsPage from './pages/SettingsPage'
import Reserve1 from './pages/reserve1';
import MapPage from './pages/reserve2';
import './App.css';
import HelpPage from './pages/HelpPage'

const App: React.FC = () => {
    document.title = 'Smart Parking Lot'

    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/home" element={<Homepage />} />
                <Route path="/signup" element={<SignupPage />} />
                <Route path="/forgot-password" element={<ForgotPasswordPage />} />
                <Route path="/admin" element={<ProfileAdminPage />} />
                <Route path="/admin-parking" element={<AdminParkingPage />} />
                <Route path="/admin-panel" element={<AdminPanelPage />} />
                <Route path="/admin-panel/add-parking-lot" element={<AddParkingLotPage />} />
                <Route path="/admin-panel/edit-parking-lot/:id" element={<EditParkingLotPage />} />
                <Route path='/balance' element={<AccountBalance />} />
                <Route path="/settings" element={<SettingsPage />} />
                <Route path="/profiles" element={<Profiles />} />
                <Route path="/profiles/messages" element={<Messages />} />
                <Route path="/profiles/cars" element={<Cars />} />
                <Route path="/profiles/details" element={<Details />} />
                <Route path="/profiles/reserve" element={<Reserve1 />} />
                <Route path="/profile/reserve" element={<Reserve1 />} />
                <Route path="/home/reserve1" element={<Reserve1 />} />
                <Route path="/home/reserve1/reserve2" element={<MapPage />} />
                <Route path="/help" element={<HelpPage />} />
            </Routes>
        </Router>
    )
}

export default App;