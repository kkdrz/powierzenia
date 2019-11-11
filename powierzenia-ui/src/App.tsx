import React from 'react';
import './App.css';
import {ButtonToolbar, Button} from 'react-bootstrap';

const App: React.FC = () => {
    return (
        <div className="App">
            <div>
                <ButtonToolbar>
                    <Button variant="primary">Primary</Button>
                    <Button variant="secondary">Secondary</Button>
                    <Button variant="success">Success</Button>
                    <Button variant="warning">Warning</Button>
                    <Button variant="danger">Danger</Button>
                    <Button variant="info">Info</Button>
                    <Button variant="light">Light</Button>
                    <Button variant="dark">Dark</Button>
                    <Button variant="link">Link</Button>
                </ButtonToolbar>
            </div>
        </div>
    );
};

export default App;
